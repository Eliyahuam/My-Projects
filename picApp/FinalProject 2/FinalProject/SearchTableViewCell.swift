//
//  SearchTableViewCell.swift
//  FinalProject
//
//  Created by mac on ט״ז בשבט תשע״ז.
//  Copyright © 5777 mac. All rights reserved.
//

import UIKit

class SearchTableViewCell: UITableViewCell {

    @IBOutlet weak var theimage: UIImageView!
    @IBOutlet weak var descriptionTV: UITextView!
 
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        theimage.layer.borderWidth = 1
        theimage.layer.masksToBounds = false
        
        theimage.layer.cornerRadius = theimage.frame.height/2
        theimage.clipsToBounds = true

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
