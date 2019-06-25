import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../service/api.service';

@Component({
  selector: 'app-create-form',
  templateUrl: './create-form.component.html',
  styleUrls: ['./create-form.component.less']
})
export class CreateFormComponent implements OnInit {

  constructor(private route: ActivatedRoute, private apiService: ApiService){
    
  }

  ngOnInit() {
  }

}
