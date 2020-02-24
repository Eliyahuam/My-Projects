//
//  Picture+Sql.swift
//  FinalProject
//
//  Created by mac on י״ד בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import Foundation


extension Picture {
    
    static func createTable(_ database:OpaquePointer?) -> Bool {
        var errormsg: UnsafeMutablePointer<Int8>? = nil
        
        
        let res = sqlite3_exec(database, "CREATE TABLE IF NOT EXISTS IMAGE_TABLE ( IMG_DECRIPTION TEXT PRIMARY KEY, USERID TEXT, IMG_URL TEXT, IMG_NAME TEXT, IMGDETAILES TEXT, LASTUPDATE DOUBLE)", nil, nil, &errormsg);
        if(res != 0){
            print("error creating table");
            return false
        }
        return true
    }
    
    func addPicture(_ uid:String, _ database:OpaquePointer?) {
        var sqlite3_stmt: OpaquePointer? = nil
        
        
        if (sqlite3_prepare_v2(database,"INSERT OR REPLACE INTO IMAGE_TABLE(IMG_DECRIPTION,USERID,IMG_URL,IMG_NAME,IMGDETAILES,LASTUPDATE) VALUES (?,?,?,?,?,?);", -1, &sqlite3_stmt, nil) == SQLITE_OK){
            
            
            let imageDesc = self.description?.cString(using: .utf8)
            let userid = uid.cString(using: .utf8)
            let imgUrl = self.imageUrl?.cString(using: .utf8)
            let imageName = self.imageName?.cString(using: .utf8)
            let imageDetailes = self.imageDetailes?.cString(using: .utf8)
            
            
            sqlite3_bind_text(sqlite3_stmt, 1, imageDesc,-1,nil);
            sqlite3_bind_text(sqlite3_stmt, 2, userid,-1,nil);
            sqlite3_bind_text(sqlite3_stmt, 3, imgUrl,-1,nil);
            sqlite3_bind_text(sqlite3_stmt, 4, imageName,-1,nil);
            sqlite3_bind_text(sqlite3_stmt, 5, imageDetailes,-1,nil);
            
            
            if (lastUpdate == nil){
                lastUpdate = Date()
            }
            sqlite3_bind_double(sqlite3_stmt, 6, lastUpdate!.toFirebase());
            
            if(sqlite3_step(sqlite3_stmt) == SQLITE_DONE){
                print("new row added to Image Table")
            }
        }
        sqlite3_finalize(sqlite3_stmt)
    }
    
    static public func getAllPicsByUid(_ uid:String, _ database:OpaquePointer?) -> [Picture] {
        var picList = [Picture]()

        var sqlite3_stmt: OpaquePointer? = nil
        let SQLITE_TRANSIENT = unsafeBitCast(-1, to: sqlite3_destructor_type.self)
        
        if (sqlite3_prepare_v2(database,"SELECT * from IMAGE_TABLE where USERID = ?;",-1,&sqlite3_stmt,nil) == SQLITE_OK){
            sqlite3_bind_text(sqlite3_stmt, 1, uid.cString(using: .utf8), -1,SQLITE_TRANSIENT);
            while(sqlite3_step(sqlite3_stmt) == SQLITE_ROW){
             
                
                let imageDescription = String(validatingUTF8:sqlite3_column_text(sqlite3_stmt,0))
                let imageUrl = String(validatingUTF8:sqlite3_column_text(sqlite3_stmt,2))
                let imageName = String(validatingUTF8:sqlite3_column_text(sqlite3_stmt,3))
                let imageDetailes = String(validatingUTF8:sqlite3_column_text(sqlite3_stmt,4))
                let pic = Picture(imageDescription!,imageUrl!,imageDetailes!,imageName!)
                picList.append(pic)
            }
            
        }
        sqlite3_finalize(sqlite3_stmt)
        return picList
    }
    
}
