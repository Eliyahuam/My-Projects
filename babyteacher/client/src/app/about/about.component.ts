import { Component, OnInit } from '@angular/core';
import {LocationService} from "../services/location.service";
import {Location} from'../location';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css'],
  providers:[LocationService]

})
export class AboutComponent implements OnInit {
  locations :Location[];
  location :Location[];
  teachersBy: Location;  
  _id?: string;
  x: number;  
  y: number;  
  constructor(private  locationService:LocationService) { }

  title: string = 'My first AGM project';
  /// the map locationat first
  lat: number = 32.016088;
  lng: number = 34.742720;
  
  ngOnInit() {
    this.locationService.getLocation()
  .subscribe(locations =>
  this.locations = locations );
  
  }
}
