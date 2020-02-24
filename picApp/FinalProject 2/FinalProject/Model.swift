//
//  Model.swift
//  FinalProject
//
//  Created by mac on י״ב בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import Foundation

extension Date {
    
    func toFirebase()->Double{
        return self.timeIntervalSince1970 * 1000
    }
    
    static func fromFirebase(_ interval:String)->Date{
        return Date(timeIntervalSince1970: Double(interval)!)
    }
    
    static func fromFirebase(_ interval:Double)->Date{
        if (interval>9999999999){
            return Date(timeIntervalSince1970: interval/1000)
        }else{
            return Date(timeIntervalSince1970: interval)
        }
    }
    
    var stringValue: String {
        let formatter = DateFormatter()
        formatter.timeZone = TimeZone.current
        formatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        return formatter.string(from: self)
    }
    
}

class Model {
    static let instance  = Model()
    lazy private var modelSql:ModelSql? = ModelSql()
    lazy private var modelFirebase:ModelFirebase? = ModelFirebase()
    
    private init() {
        
    }
    
    public func addPicture(_ uid:String,_ pic:Picture) {
        
        pic.addPicture(uid, modelSql?.database)
        modelFirebase?.addPicture(uid, pic)

    }
    
    
    public func getPicByDescription(description:String, callback:@escaping ([Picture]) -> Void) {
        modelFirebase?.getPicByDescription(description: description, callback: { (picList) in
           callback(picList)
        })
    }
    public func getAllPicsByUid(uid:String, callback:@escaping ([Picture]) -> Void) {
      
       // modelFirebase?.getAllPicsByUid(uid: uid, callback: { (picList) in
        //   callback(picList)
       // })
       // let picList = Picture.getAllPicsByUid(uid , modelSql?.database)
        //callback(picList)
        
        //get the last update from sqlite table
        let lastUpdateDate = LastUpdateTable.getLastUpdateDate(database: modelSql?.database, table: "LAST_UPDATE")
        
        
        //get all updated records from firebase
        modelFirebase?.getAllPicsByUid(uid: uid, lastUpdateDate, callback: { (picList) in
            print("got \(picList.count) new records from FB")
            var lastUpdate:Date?
            //update the local DB
            for pic in picList {
                pic.addPicture(uid, self.modelSql?.database)
                if lastUpdate == nil {
                    lastUpdate = pic.lastUpdate
                }
                else {
                    if lastUpdate!.compare(pic.lastUpdate!) == ComparisonResult.orderedAscending {
                        lastUpdate = pic.lastUpdate
                    }
                }
            }
            //update the lastUpdate in local DB
            if (lastUpdate != nil) {
                LastUpdateTable.setLastUpdate(database: self.modelSql!.database, table: "LAST_UPDATE", lastUpdate: lastUpdate!)
            }
            //get the complete list from local DB
            let totalList = Picture.getAllPicsByUid(uid, self.modelSql?.database)
            callback(totalList)

        })
        
    }
    
    
    
}
