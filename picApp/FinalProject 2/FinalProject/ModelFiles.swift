//
//  ModelFiles.swift
//  FinalProject
//
//  Created by mac on ט״ו בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import Foundation
import UIKit

class ModelFiles {
    
    static public let instance = ModelFiles()
    

    lazy private var modelLocalFiles:ModelLocalFiles? = ModelLocalFiles()
    lazy private var modelFirebaseStorage:ModelFirebaseStorage? = ModelFirebaseStorage()
    
    private init() {
        
    }
    public func saveImage(image:UIImage,name:String ,callback: @escaping (String?) -> Void) {
        //save the image to firebase
        modelFirebaseStorage?.saveImageToFirebase(image: image, name: name, callback: { (url) in
            if (url != nil) {
                //save the image to local store
                
            self.modelLocalFiles?.saveImageToFile(image: image, name: name)
            }
            callback(url)
        })
        
    }
    public func getImage(name:String, callback: @escaping (UIImage) -> Void){
        
        let url = URL(string: name)
        let imageLocalName = url?.lastPathComponent
        //try to get the file from Local Store
        if let image = modelLocalFiles?.getImageFromFile(name: imageLocalName!) {
            callback(image)
        }
        else {
            modelFirebaseStorage?.getImageFromFirebase(url: name, callback: { (image) in
                if image != nil {
                    self.modelLocalFiles?.saveImageToFile(image: image!, name: imageLocalName!)
                }
                callback(image!)
            })
            
        }
    }
    
}
