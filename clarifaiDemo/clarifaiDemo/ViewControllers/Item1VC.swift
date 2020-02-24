//
//  ViewController.swift
//  clarifaiDemo
//
//  Created by Eliyahu Barsheshet on 24/05/2019.
//  Copyright © 2019 Eliyahu Barsheshet. All rights reserved.
//

import UIKit
import Clarifai
import MBCircularProgressBar


class Item1VC: UIViewController , UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    @IBOutlet weak var genderImage: UIImageView!
    @IBOutlet weak var ageProgress: MBCircularProgressBarView!
    @IBOutlet weak var button: UIButton!
    @IBOutlet weak var textView: UITextView!
    @IBOutlet weak var imageView: UIImageView!
    
    
   
    
    var app:ClarifaiApp?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        imageView.roundedImage()
        
    }

    
    @IBAction func button(_ sender: Any) {
        let imagePicker = UIImagePickerController()
        imagePicker.delegate = self;
        
        let actionSheet = UIAlertController(title: "Photo source", message: "Choose a source", preferredStyle: .actionSheet)
        
        actionSheet.addAction(UIAlertAction(title: "Camera", style: .default, handler: { (action:UIAlertAction) in
            if UIImagePickerController.isSourceTypeAvailable(.camera) {
            
            imagePicker.sourceType = .camera
            self.present(imagePicker,animated: true,completion: nil)
            } else {
                print("Camera not available")
            }
        }))
        actionSheet.addAction(UIAlertAction(title: "Photo Library", style: .default, handler: { (action:UIAlertAction) in
            imagePicker.sourceType = .photoLibrary
            self.present(imagePicker,animated: true,completion: nil)
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        
        self.present(actionSheet,animated: true , completion: nil)
        //picker.allowsEditing = false;
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
       
        if let image = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            imageView.image = image
            picker.dismiss(animated: true, completion: nil)
            textView.text = "Recognizing..."
            button.isEnabled = false
            
            ModelRecognize.model.recognizeImage(image: image, completion: { (data) in
                DispatchQueue.main.async {

                    self.textView.text = "Done"
                    self.setGenderAgeUi(age: data?.object(at: 0) as! String, gender: data?.object(at: 1) as! String)
                    
                    self.button.isEnabled = true;
                }
            })
        }
    }
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: nil)
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


    



