//
//  FireBaseAuth.swift
//  FinalProject
//
//  Created by mac on כ״ח בטבת תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import Foundation
import Firebase


class FireBaseAuth {
    
    var name:String?
    var email:String?
    var password:String?
    
    init() {
        
    }
    
    init (_ name:String!,_ email:String,_ password:String) {
        self.name = name
        self.email = email
        self.password = password
    }
    
    public func getUid(completion: @escaping (String?)->()) {
        let userId = FIRAuth.auth()?.currentUser?.uid
        completion(userId)
        
    }
    
    public func createUser(completion: @escaping (String?)->())  {
        
        FIRAuth.auth()?.createUser(withEmail: email!, password: password!, completion: {(user, error) in
            if error != nil {
                completion(error?.localizedDescription)
            }
            else {
                //successfully authonticated user
                
                let ref = FIRDatabase.database().reference(fromURL: "https://finalprojectios-8d831.firebaseio.com/")
                let userRef = ref.child("users").child((user?.uid)!)
                let values = ["name" : self.name, "email": self.email]
                userRef.updateChildValues(values, withCompletionBlock: { (Err, ref) in
                    if Err != nil {
                        print(Err!)
                        return
                    }
                    print("saved user successfully")
                    completion(error?.localizedDescription)
                })
            }
        })
        
    }
    
    public func loignAuth(completion: @escaping (String?)->()) {
        
        FIRAuth.auth()?.signIn(withEmail: self.email!, password: self.password!, completion: { (user, error) in
            
            completion(error?.localizedDescription)
            
            
        })
        
        
    }
    public func logOut() {
        do {
            try FIRAuth.auth()?.signOut()
        }
        catch let logoutError {
            print(logoutError)
        }
    }
    
}
