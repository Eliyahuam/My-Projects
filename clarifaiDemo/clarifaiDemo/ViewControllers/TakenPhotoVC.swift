//
//  TakenPhotoVC.swift
//  clarifaiDemo
//
//  Created by Eliyahu Barsheshet on 25/05/2019.
//  Copyright © 2019 Eliyahu Barsheshet. All rights reserved.
//

import UIKit
import MBCircularProgressBar
class TakenPhotoVC: UIViewController {


    @IBOutlet weak var ageProgress: MBCircularProgressBarView!
    @IBOutlet weak var genderImage: UIImageView!
    @IBOutlet weak var textView: UITextView!
    @IBOutlet weak var imageView: UIImageView!
    var takenPhoto:UIImage?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        imageView.roundedImage()
        
        if let availablePhoto = takenPhoto {
            imageView.image = availablePhoto
            setViewData()
        }
    }
    

    
    @IBAction func backBtton(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func setViewData() {
        textView.text = "Recognizing..."
        ModelRecognize.model.recognizeImage(image: imageView.image!, completion: { (data) in
            DispatchQueue.main.async {
                
                self.textView.text = "Done"
                self.setGenderAgeUi(age: data?.object(at: 0) as! String, gender: data?.object(at: 1) as! String)
                
            }
        })
    }
    
    func setGenderAgeUi(age:String,gender:String) {
        
        UIView.animate(withDuration: 1.5) {
            self.ageProgress.value = CGFloat((age as NSString).floatValue)
            if  gender == "masculine" {
                self.genderImage.image = UIImage(named: "male-512")
            }
            else {
                self.genderImage.image = UIImage(named: "female-512")
            }
        }
    }

}
