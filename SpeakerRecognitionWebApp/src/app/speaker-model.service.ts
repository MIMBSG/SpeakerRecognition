import { Injectable } from '@angular/core';
import { SpeakerModel } from './SpeakerModel';
import {SpeakerResponse} from './SpeakerResponse';
import { MfccService } from './mfcc.service';
const decode = require('audio-decode');

@Injectable()
export class SpeakerModelService {

  private speakerModel: SpeakerModel;


  constructor(private mfccService: MfccService) { }

  public getSpeaker(): SpeakerModel{
    return this.speakerModel;
  }
  public setSpeaker(speaker:SpeakerModel): void{
      this.speakerModel = speaker;
  }

  private recognition(mfcc: number[][]) {
    return new Promise(function (resolve,reject){
      let request: XMLHttpRequest = new XMLHttpRequest();
      let url: string = "http://localhost:8090/rest/user/recognition/" + mfcc.toString() + "/";
      var scope = this;
      request.onreadystatechange = function() {
        if (request.readyState === 4) {
            if (request.status === 200) {
                resolve(request.responseText);
                }
            else
                console.log("The computer appears to be offline.");
        }
        };
      request.open("POST", url , true);
      request.send(null);
    });
  }
  private register(mfcc: number[][], speakerModel: SpeakerModel){
    return new Promise(function (resolve,reject){
      let request: XMLHttpRequest = new XMLHttpRequest();
      let url: string = "http://localhost:8090/rest/user/register/" + mfcc.toString() + "/" + speakerModel.name + "/" + speakerModel.lastName + "/";
      var scope = this;
      request.onreadystatechange = function() {
        if (request.readyState === 4) {
            if (request.status === 200) {
                resolve(request.responseText);
                }
            else
                console.log("The computer appears to be offline.");
        }
        };
      request.open("POST", url , true);
      request.send(null);
    });
  }
  public readAndSendFile(file: Blob, speakerModel: SpeakerModel) {
    var scope = this;
    return new Promise(function (resolve,reject){
      let reader: FileReader = new FileReader();
      reader.onloadend = function () {
        decode(reader.result).then( audioBuffer => {
          let data: Float32Array = new Float32Array(audioBuffer.getChannelData(0).length);
          data = audioBuffer.getChannelData(0);
          let averageEnergy: number = scope.averageEnergyOfSignal(data);
          data = scope.preScaleData(data);
          scope.printAudioBufferDetails(audioBuffer);
          console.log("Average energy: " +  averageEnergy);
          if(averageEnergy < 0.009){
            resolve("Please try again and speak louder.");
          }
          else
            if(speakerModel == null){
              if(audioBuffer.duration < 3 || audioBuffer.duration > 8.99){
                resolve("To login please speak longer than three but less than nine seconds.");
              }
              else{
                  let mfcc: number[][] = scope.mfccService.extractMfcc(Array.prototype.slice.call(data), audioBuffer.sampleRate);
                  scope.recognition(mfcc).then(function(result){
                  let speaker: SpeakerResponse[] = JSON.parse(result.toString());
                  resolve(speaker);}).catch(function(){});
              }
            }
            else{
              if(audioBuffer.duration < 5 || audioBuffer.duration > 8.99){
                resolve("To register please speak longer than five but less than nine seconds.");
              }
              else{
                  let mfcc: number[][] = scope.mfccService.extractMfcc(Array.prototype.slice.call(data), audioBuffer.sampleRate);
                  scope.register(mfcc,speakerModel).then(function(result){
                  let speaker: SpeakerModel = JSON.parse(result.toString());
                  resolve(speaker);}).catch(function(){});
              }
            }
        });
      }
      reader.readAsArrayBuffer(file);});
  }

  private preScaleData(data: Float32Array): Float32Array{
    for(let i: number = 0; i < data.length; i++){
             data[i] = Math.ceil(data[i] * 32768);
           }
    return data;
  }

  private averageEnergyOfSignal(data: Float32Array): number{
    let averageEnergy: number = 0;
    for(let i: number = 0; i < data.length; i++){
      averageEnergy = averageEnergy + Math.pow(data[i],2);
    }
    return averageEnergy/data.length;
  }

  private printAudioBufferDetails(audioBuffer: any): void{
    console.log("Frequency = " + audioBuffer.sampleRate);
    console.log("Channels = " + audioBuffer.numberOfChannels);
    console.log("Duration = " + audioBuffer.duration);
  }
}
