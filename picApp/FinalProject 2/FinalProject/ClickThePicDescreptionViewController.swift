//
//  ClickThePicDescreptionViewController.swift
//  FinalProject
//
//  Created by mac on י״ב בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import UIKit

class ClickThePicDescreptionViewController: UIViewController {

    @IBOutlet weak var decreptionTV: UITextView!
    @IBOutlet weak var theimage: UIImageView!
    var url:String?
    var textToDisplay:String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if textToDisplay != nil {
            decreptionTV.text = textToDisplay
        }
        if url != nil {
            ModelFiles.instance.getImage(name: url!) { (myImage) in
                
                self.theimage.image = myImage
                
            }
        }

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
