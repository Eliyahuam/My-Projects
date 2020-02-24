//
//  CustomViewController.swift
//  FinalProject
//
//  Created by mac on ט׳ בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import UIKit

class CustomViewController: UIViewController {

    @IBOutlet weak var containerView: UIView!
    var registerVC:UIViewController?
    var loginVC:UIViewController?
    
    @IBOutlet weak var loginUiBtn: UIButton!
    @IBOutlet weak var registerUiBtn: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let storyBoard = UIStoryboard(name: "Main", bundle: nil)
        self.registerVC = storyBoard.instantiateViewController(withIdentifier: "registerView")
        self.loginVC = storyBoard.instantiateViewController(withIdentifier: "loginView")
        defenitions()
        
        // Do any additional setup after loading the view.
    }
    @IBAction func registerPressed(_ sender: Any) {
        for view in self.containerView.subviews {
            view.removeFromSuperview()
        }
        self.registerVC!.view.frame = self.containerView.bounds
        self.containerView.addSubview((self.registerVC!.view)!)
        
    }

    @IBAction func loginPressed(_ sender: Any) {
        for view in self.containerView.subviews {
            view.removeFromSuperview()
        }
        self.loginVC!.view.frame = self.containerView.bounds
        self.containerView.addSubview((self.loginVC!.view)!)
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func defenitions() {
        self.loginVC!.view.frame = self.containerView.bounds
        self.containerView.addSubview((self.loginVC!.view)!)
        loginUiBtn.layer.cornerRadius = 5
        registerUiBtn.layer.cornerRadius = 5
        loginUiBtn.backgroundColor = .clear
        loginUiBtn.layer.borderWidth = 1
        loginUiBtn.layer.borderColor = UIColor.white.cgColor
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
