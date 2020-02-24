//
//  User.swift
//  FinalProject
//
//  Created by mac on י״ב בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import Foundation
import FirebaseDatabase

class Picture {
   
    var description:String?
    var imageUrl:String?
    var imageDetailes:String?
    var imageName:String?
    var lastUpdate:Date?
    
    init(_ json:Dictionary<String,Any>) {
        
        self.description = json["description"] as! String?
        self.imageUrl = json["url"] as! String?
        self.imageDetailes = json["imageDetailes"] as! String?
        self.imageName = json["image_Name"] as! String?
        
        if let ts = json["lastUpdate"] as? Double{
            self.lastUpdate = Date.fromFirebase(ts)
        }
        
    }
    init ( _ description:String, _ imageUrl:String, _ imageDetailes:String , _ imageName:String) {
        
        self.description = description
        self.imageUrl = imageUrl
        self.imageDetailes = imageDetailes
        self.imageName = imageName
    }
    
    func toFirebase() -> Dictionary<String,Any> {
        var json = Dictionary<String,Any>()
        
        json["description"] = description
        json["url"] = imageUrl
        json["imageDetailes"] = imageDetailes
        json["image_Name"] = imageName
        json["lastUpdate"] = FIRServerValue.timestamp()
        
        
        return json
    }
    func picDataToFirebase() -> Dictionary<String,Any> {
        var json = Dictionary<String,Any>()
        json["imageDetailes"] = imageDetailes
        json["url"] = imageUrl
        
        return json
    }
}
