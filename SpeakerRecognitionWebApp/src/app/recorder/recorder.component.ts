import { Component } from '@angular/core';
import {SpeakerModelService} from 'app/speaker-model.service';
import {SpeakerModel} from '../SpeakerModel';
import {SpeakerResponse} from '../SpeakerResponse';
declare const navigator: any;
declare const MediaRecorder: any;

@Component({
  selector: 'app-recorder',
  templateUrl: './recorder.component.html',
  styleUrls: ['./recorder.component.css']
})
export class RecorderComponent {

    private isRecording: boolean = false;
    private isRecognized: boolean = false;
    private speakerModel: SpeakerModel;
    private speakerResponse: SpeakerResponse[];
    private chunks: any = [];
    private mediaRecorder: any;

  constructor(private speakerModelService: SpeakerModelService) {
      navigator.getUserMedia = (navigator.getUserMedia ||
        navigator.webkitGetUserMedia ||
        navigator.mozGetUserMedia ||
        navigator.msGetUserMedia);
      navigator.getUserMedia({ audio: true }, this.onSuccess, e => console.log(e));
    }

    private record(): void {
      document.getElementById('message').innerHTML = "Recording...";
      this.isRecording = true;
      this.isRecognized = false;
      this.mediaRecorder.start();
    }

    private stop(): void {
      this.isRecording = false;
      this.mediaRecorder.stop();
    }

    private onSuccess = stream => {
        this.mediaRecorder = new MediaRecorder(stream);
        this.mediaRecorder.onstop = e => {
          var scope = this;
          const blob = new Blob(this.chunks, { 'type': 'audio/wav' });
          scope.speakerModel = this.speakerModelService.getSpeaker();
          if(scope.speakerModel == null){
            document.getElementById('message').innerHTML = "Recognizing...";
            this.speakerModelService.readAndSendFile(blob,null).then(function(result){
                if(typeof(result)!="string"){
                  scope.speakerResponse = JSON.parse(JSON.stringify(result));
                  console.log(result);
                  if(scope.speakerResponse[0].name == "NotFound"){
                    document.getElementById('message').innerHTML = "Speaker not found, try again. The closest speakers: <br>"
                    + scope.speakerResponse[1].name + " " + scope.speakerResponse[1].lastName + ",<br>"
                    + scope.speakerResponse[2].name + " " + scope.speakerResponse[2].lastName + ",<br>"
                    + scope.speakerResponse[3].name + " " + scope.speakerResponse[3].lastName + ".";
                  }
                  else
                  document.getElementById('message').innerHTML = "Welcome " + scope.speakerResponse[0].name + " " + scope.speakerResponse[0].lastName + " !";
                }
                else
                  document.getElementById('message').innerHTML = result.toString();
            }).catch(function(){});
                this.speakerModelService.setSpeaker(null);
          }
          else{
            document.getElementById('message').innerHTML = "Register...";
            this.speakerModelService.readAndSendFile(blob,scope.speakerModel).then(function(result){
                if(typeof(result)!="string"){
                  scope.speakerModel = JSON.parse(JSON.stringify(result));
                  document.getElementById('message').innerHTML = "Registered in database as " + scope.speakerModel.name + " " + scope.speakerModel.lastName + " !";
                  scope.speakerModelService.setSpeaker(null);
                }
                else
                  document.getElementById('message').innerHTML = result.toString();
              }).catch(function(){});
          }
          this.chunks.length = 0;
        };
        this.mediaRecorder.ondataavailable = e => this.chunks.push(e.data);
    }
}
