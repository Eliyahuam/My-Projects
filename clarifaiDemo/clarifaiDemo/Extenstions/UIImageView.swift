//
//  UIImageView.swift
//  clarifaiDemo
//
//  Created by Eliyahu Barsheshet on 25/05/2019.
//  Copyright © 2019 Eliyahu Barsheshet. All rights reserved.
//

import Foundation
import UIKit

extension UIImageView {
    func roundedImage() {
        self.layer.cornerRadius = self.frame.size.width / 2
        self.clipsToBounds = true
        self.layer.borderWidth = 4
    }
}
