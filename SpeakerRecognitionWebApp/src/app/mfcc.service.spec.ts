import { TestBed, inject } from '@angular/core/testing';

import { MfccService } from './mfcc.service';

describe('MfccService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MfccService]
    });
  });

  it('should be created', inject([MfccService], (service: MfccService) => {
    expect(service).toBeTruthy();
  }));
});
