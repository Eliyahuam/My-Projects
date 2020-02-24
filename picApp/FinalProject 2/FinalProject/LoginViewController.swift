//
//  LoginViewController.swift
//  FinalProject
//
//  Created by mac on כ״ה בטבת תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import UIKit


class LoginViewController: UIViewController {

    @IBOutlet weak var tfEmail: UITextField!
    @IBOutlet weak var tfPassword: UITextField!
    @IBOutlet weak var loginContainer: UIView!
    @IBOutlet weak var loginBTN: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()

        loginDefinistions()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func loginDefinistions() {
        
        loginContainer.layer.cornerRadius = 5
        tfPassword.isSecureTextEntry = true
        loginBTN.layer.cornerRadius = 5
    
    }
    
    @IBAction func loginAuthPressed(_ sender: Any) {
        let user = FireBaseAuth(nil ,tfEmail.text!, tfPassword.text!)
        user.loignAuth { (err) -> () in
            if err != nil {
                let alert = UIAlertController.init(title: nil, message: err, preferredStyle: UIAlertControllerStyle.alert)
                let okAction = UIAlertAction.init(title: "Ok", style: UIAlertActionStyle.default, handler: nil)
                alert.addAction(okAction)
                self.present(alert, animated: true, completion: nil)
                return
            }
            else {
                print("successfull")
                self.performSegue(withIdentifier: "firstAppView", sender: self)
                
            }
        }
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
