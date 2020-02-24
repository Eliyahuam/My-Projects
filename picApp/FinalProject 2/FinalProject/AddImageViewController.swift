//
//  AddImageViewController.swift
//  FinalProject
//
//  Created by mac on י״ב בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import UIKit
import SVProgressHUD



class AddImageViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    @IBOutlet weak var imgaeDescription: UITextField!
    @IBOutlet weak var imageName: UITextField!

    @IBOutlet weak var imageToUpload: UIImageView!
    var imageUrl:String?
   
    let currentUser = FireBaseAuth()
    
    

    override func viewDidLoad() {
        super.viewDidLoad()
        
    
        imageToUpload.layer.borderWidth = 1
       
        
        
       

        // Do any additional setup after loading the view.
    }
    @IBAction func addPressed(_ sender: Any) {
        if (imgaeDescription.text != "" && imageName.text != "" && imageToUpload.image != nil) {
        currentUser.getUid(completion: { (uid) in
            SVProgressHUD.show(withStatus: "Uploading")
            
            let date = NSDate()
            let myDateString = String(Int64(date.timeIntervalSince1970*1000))
           ModelFiles.instance.saveImage(image: self.imageToUpload.image!, name: self.imageName.text! + "_" + myDateString, callback: { (url) in
            
           
            
            let pic = Picture(self.imgaeDescription.text! + "_" + myDateString, url!, self.imgaeDescription.text! , self.imageName.text! + "_" + myDateString)
            
            Model.instance.addPicture(uid!,pic)
            
            SVProgressHUD.dismiss()
            _ = self.navigationController?.popViewController(animated: true)
            
           })
            
           
        })
        
        }
        else {
            let alert = UIAlertController.init(title: nil, message: "Somthing missing please fill all fields", preferredStyle: UIAlertControllerStyle.alert)
            let okAction = UIAlertAction.init(title: "Ok", style: UIAlertActionStyle.default, handler: nil)
            alert.addAction(okAction)
            self.present(alert, animated: true, completion: nil)
        }


        
    }

    @IBAction func getPicBtn(_ sender: Any) {
        
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.camera) {
            let imagePicker = UIImagePickerController()
            imagePicker.delegate = self
            imagePicker.sourceType = UIImagePickerControllerSourceType.camera
            imagePicker.allowsEditing = true
            self.present(imagePicker, animated: true, completion: nil)
        }
        else {
            if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.photoLibrary) {
                let imagePicker = UIImagePickerController()
                 imagePicker.delegate = self
                imagePicker.sourceType = UIImagePickerControllerSourceType.photoLibrary
                imagePicker.allowsEditing = true
                self.present(imagePicker, animated: true, completion: nil)
        }
    }
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
    
    
    
    public func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        
        if let image = info[UIImagePickerControllerOriginalImage] as? UIImage {
            imageToUpload.image = image
            
        }else {
            print("wrong")
        }
        
        self.dismiss(animated: false, completion: nil)
        
    }

}
