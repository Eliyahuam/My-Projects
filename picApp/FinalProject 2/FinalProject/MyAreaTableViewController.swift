//
//  MyAreaTableViewController.swift
//  FinalProject
//
//  Created by mac on י״ב בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import UIKit
//import Firebase

class MyAreaTableViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var myPicTableView: UITableView!
    
    var picList = [Picture]()

    
    let currentUser = FireBaseAuth()
    
 
        override func viewWillAppear(_ animated: Bool) {
        currentUser.getUid(completion: { (uid) in
            Model.instance.getAllPicsByUid(uid: uid!, callback: { (pictureList) in
                self.picList = pictureList
                self.myPicTableView.reloadData()
    
            })
    
    
        })
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        
        currentUser.getUid(completion: { (uid) in
            Model.instance.getAllPicsByUid(uid: uid!, callback: { (pictureList) in
                self.picList = pictureList
                
               // for pic in self.picList {
                //    Model.instance.addPicture(uid!, pic)
                //}
                self.myPicTableView.reloadData()
            })
            
        })
        
        
        
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
   
     public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
       
        return picList.count
        
    }
    
    
     public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let rowcell = self.myPicTableView.dequeueReusableCell(withIdentifier: "myPicCell", for: indexPath)
        let imageAndDescreptionCell = rowcell as! myPicTableViewCell
        
        imageAndDescreptionCell.cellDescreption.text = self.picList[indexPath.row].imageDetailes

        ModelFiles.instance.getImage(name: picList[indexPath.row].imageUrl!) { (myImage) in
        
            imageAndDescreptionCell.theImage.image = myImage
           
        }
        
        
        return rowcell
    }
    var selectedIndex:Int?
    public func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        selectedIndex = indexPath.row
        self.performSegue(withIdentifier: "presentCellDescreption", sender: self)
    }
    

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
        if let descriptionVC = segue.destination as? ClickThePicDescreptionViewController{
            descriptionVC.textToDisplay = picList[self.selectedIndex!].imageDetailes
            descriptionVC.url = picList[self.selectedIndex!].imageUrl
            
        }
    }
    

}
