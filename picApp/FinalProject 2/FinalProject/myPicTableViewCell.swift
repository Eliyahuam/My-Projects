//
//  myPicTableViewCell.swift
//  FinalProject
//
//  Created by mac on י״ב בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import UIKit

class myPicTableViewCell: UITableViewCell {

    @IBOutlet weak var cellDescreption: UITextView!
    @IBOutlet weak var theImage: UIImageView!
 

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        theImage.layer.borderWidth = 1
        theImage.layer.masksToBounds = false
        
        theImage.layer.cornerRadius = theImage.frame.height/2
        theImage.clipsToBounds = true
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
