//
//  SearchTableViewController.swift
//  FinalProject
//
//  Created by mac on י״ב בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import UIKit

class SearchTableViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var searchTableView: UITableView!
    @IBOutlet weak var searchTV: UITextField!
    
    var picList = [Picture]()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    @IBAction func searchBtnPressed(_ sender: UIButton) {
        
        Model.instance.getPicByDescription(description: searchTV.text!) { (picturelist) in
            self.picList = picturelist
            self.searchTableView.reloadData()
        }
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
    
    var selectedIndex:Int?
    public func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        selectedIndex = indexPath.row
        self.performSegue(withIdentifier: "presentCellDescreptionFromSearch", sender: self)
    }
    
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return picList.count
        
    }
    
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let rowcell = self.searchTableView.dequeueReusableCell(withIdentifier: "searchCell", for: indexPath)
        let imageAndDescreptionCell = rowcell as! SearchTableViewCell
        
       
        imageAndDescreptionCell.descriptionTV.text = self.picList[indexPath.row].imageDetailes
        ModelFiles.instance.getImage(name: picList[indexPath.row].imageUrl!) { (myImage) in
       
            imageAndDescreptionCell.theimage.image = myImage
            
        }
        return rowcell
    }
    
    

}
