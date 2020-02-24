//
//  Model.swift
//  clarifaiDemo
//
//  Created by Eliyahu Barsheshet on 25/05/2019.
//  Copyright © 2019 Eliyahu Barsheshet. All rights reserved.
//

import Foundation
import UIKit
import Clarifai

class ModelRecognize {
    static let model = ModelRecognize()
    var app:ClarifaiApp?
    var rcDetailes:[String]?
    
    private init() {
        self.app = ClarifaiApp(apiKey: "e5ff79cc964646dc8a14b699cb6c3ac4")
    }
    

   public func recognizeImage(image: UIImage, completion: @escaping (NSMutableArray?)-> Void )  {
        
        if let app = app {
            
            app.getModelByID("c0c0ac362b03416da06ab3fa36fb58e3", completion: { (model, error) in
                let caiImage = ClarifaiImage(image: image)!
                model!.predict(on: [caiImage], completion: { (outputs, error) in
                    print("%@", error ?? "no error")
                    guard
                        let caiOuputs = outputs
                        else {
                            print("Predict failed")
                            return
                    }
                    
                    if let caiOutput = caiOuputs.first as? ClarifaiOutputFace {
                        
                        let tags = NSMutableArray()
                        
                        for concept in caiOutput.faces {
                            tags.add(concept.ageAppearance.first?.conceptName as Any)
                            tags.add(concept.genderAppearance.first?.conceptName as Any)
                            
                           
                        }
                        completion(tags)
                    }
                })
            })
            
        }
        
    }

}
