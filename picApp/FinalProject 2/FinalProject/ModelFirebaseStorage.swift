//
//  ModelFirebaseStorage.swift
//  FinalProject
//
//  Created by mac on ט״ו בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import Foundation
import UIKit
import Firebase

class ModelFirebaseStorage {

    
    lazy var storageRef = FIRStorage.storage().reference(forURL:
        "gs://finalprojectios-8d831.appspot.com")
    
    func saveImageToFirebase(image:UIImage, name:(String), callback:@escaping (String?) -> Void) {
        let filesRef = storageRef.child(name)
        if let data = UIImageJPEGRepresentation(image, 0.8) {
            filesRef.put(data, metadata: nil) { metadata, error in
                if (error != nil) {
                    callback(nil)
                } else {
                    let downloadURL = metadata!.downloadURL()
                    callback(downloadURL?.absoluteString)
                }
            }
        }
    }
    
    func getImageFromFirebase(url:String,
                              callback:@escaping (UIImage?)->Void){
        let ref = FIRStorage.storage().reference(forURL: url)
        ref.data(withMaxSize: 10000000, completion: {(data, error) in
            if (error == nil && data != nil){
                let image = UIImage(data: data!)
                callback(image)
            }else{
                callback(nil)
            }
        })
    }
}
