//
//  ModelFirebase.swift
//  FinalProject
//
//  Created by mac on י״ד בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import Foundation
import Firebase

class ModelFirebase {

     init() {
        
    }
    
    public func addPicture(_ uid:String,_ pic:Picture) {
        //let picName:String = setImageName()
        let ref = FIRDatabase.database().reference().child("users").child(uid).child("pictures").child("image_" + pic.imageName!)
        ref.setValue(pic.toFirebase())
        let ref2 = FIRDatabase.database().reference().child("allPictures").child(pic.imageName!)
        ref2.setValue(pic.picDataToFirebase())
        
        
    }
    
    
    public func getPicByDescription(description:String, callback:@escaping ([Picture]) -> Void) {
        
    
        
        let handler = {(snapshot:FIRDataSnapshot) in
            var picList = [Picture]()
            for child in snapshot.children.allObjects{
                if let childData = child as? FIRDataSnapshot{
                    if let json = childData.value as? Dictionary<String,Any>{
                        let pic = Picture(json)
                        picList.append(pic)
                    }
                }
            }
            callback(picList)
        }
        
        let ref = FIRDatabase.database().reference().child("allPictures")
        
            let fbQuery = ref.queryOrdered(byChild:"imageDetailes").queryEqual(toValue: description)
            fbQuery.observeSingleEvent(of: .value, with: handler)
        
        
    }
    public func getAllPicsByUid(uid:String,_ lastUpdate:Date?, callback:@escaping ([Picture]) -> Void) {
        
        
        let handler = {(snapshot:FIRDataSnapshot) in
            var picList = [Picture]()
            for child in snapshot.children.allObjects{
                if let childData = child as? FIRDataSnapshot{
                    if let json = childData.value as? Dictionary<String,Any>{
                        let pic = Picture(json)
                        picList.append(pic)
                    }
                }
            }
            callback(picList)
        }
        
        let ref = FIRDatabase.database().reference().child("users").child(uid).child("pictures")
        if (lastUpdate != nil){
            print("q starting at:\(lastUpdate!) \(lastUpdate!.toFirebase())")
            let fbQuery = ref.queryOrdered(byChild:"lastUpdate").queryStarting(atValue:lastUpdate!.toFirebase() + 1)
            fbQuery.observeSingleEvent(of: .value, with: handler)
        }else{
            ref.observeSingleEvent(of: .value, with: handler)
        }
        
//        let ref = FIRDatabase.database().reference().child("users").child(uid).child("pictures")
//        ref.observeSingleEvent(of: .value, with: { (snapshot) in
//            var picList = [Picture]()
//            for child in snapshot.children.allObjects {
//                
//                if let childData = child as? FIRDataSnapshot {
//                    if let json = childData.value as? Dictionary<String,String> {
//                        let pic = Picture(json)
//                        picList.append(pic)
//                    }
//                }
//            }
//            callback(picList)
//        })
        
        
    }

    
    
    
    
   /*
    public func setImageName() -> String {
        var picName:String = Date().timeIntervalSince1970.debugDescription
        picName = picName.replacingOccurrences(of: ".", with: "", options: .literal, range: nil)
        return picName
    }
   */
    
}
