import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './/app-routing.module';

import {SpeakerModelService} from './speaker-model.service';
import {FftService} from './fft.service';
import { MatrixesService } from './matrixes.service';
import { MfccService } from './mfcc.service';

import { AppComponent } from './app.component';
import { RecorderComponent } from './recorder/recorder.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { CitationsComponent } from './citations/citations.component';


@NgModule({
  declarations: [
    AppComponent,
    RecorderComponent,
    LoginComponent,
    RegisterComponent,
    CitationsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [SpeakerModelService, MatrixesService, MfccService, FftService],
  bootstrap: [AppComponent]
})
export class AppModule { }
