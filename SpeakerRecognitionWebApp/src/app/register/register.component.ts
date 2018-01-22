import { Component } from '@angular/core';
import {SpeakerModel} from '../SpeakerModel';
import {SpeakerModelService} from 'app/speaker-model.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent{

  private speakerModel: SpeakerModel;
  private isSubmited: boolean = false;

  constructor(private speakerModelService: SpeakerModelService) {
    this.speakerModel = new SpeakerModel();
    this.speakerModel.id = 0;
    this.speakerModelService.setSpeaker(null);
  }
  public onChangeName(deviceValue) {
    this.speakerModel.name = deviceValue;
  }
  public onChangeLastName(deviceValue) {
    this.speakerModel.lastName = deviceValue;
  }
  public done(){
    if(this.speakerModel.name!=null&&this.speakerModel.lastName!=null){
      this.isSubmited = true;
      this.speakerModelService.setSpeaker(this.speakerModel);
      document.getElementById('message').innerHTML = "Record your voice.";
    }
    else
      document.getElementById('message').innerHTML = "Fill empty inputs.";
  }
}
