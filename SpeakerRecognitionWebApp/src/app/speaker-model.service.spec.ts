import { TestBed, inject } from '@angular/core/testing';

import { SpeakerModelService } from './speaker-model.service';

describe('SpeakerModelService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpeakerModelService]
    });
  });

  it('should be created', inject([SpeakerModelService], (service: SpeakerModelService) => {
    expect(service).toBeTruthy();
  }));
});
