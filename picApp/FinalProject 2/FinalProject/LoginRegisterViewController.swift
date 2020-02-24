//
//  ViewController.swift
//  FinalProject
//
//  Created by mac on כ״א בטבת תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import UIKit


class LoginRegisterViewController: UIViewController {

    @IBOutlet weak var tfEmail: UITextField!
    
    @IBOutlet weak var tfName: UITextField!
    @IBOutlet weak var tfPassword: UITextField!
   
    @IBOutlet weak var registerButton: UIButton!
    @IBOutlet weak var detailsContainer: UIView!
    
    
    @IBAction func registerBtn(_ sender: UIButton) {
        let newUser = FireBaseAuth(tfName.text!,tfEmail.text!, tfPassword.text!)
        newUser.createUser { (err) in
            if err != nil {
                let alert = UIAlertController.init(title: nil, message: err, preferredStyle: UIAlertControllerStyle.alert)
                let okAction = UIAlertAction.init(title: "Ok", style: UIAlertActionStyle.default, handler: nil)
                alert.addAction(okAction)
                self.present(alert, animated: true, completion: nil)
                return
            }
            else {
                 self.performSegue(withIdentifier: "throughRegister", sender: self)
            }

        }
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        definistions()
        
        
        
        // Do any additional setup after loading the view, typically from a nib.
    }
    func definistions() {
        detailsContainer.layer.cornerRadius = 5
        tfPassword.isSecureTextEntry = true
        registerButton.layer.cornerRadius = 5
        
    }
    @IBAction func unwindToRegisterView(segue:UIStoryboardSegue) {
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

