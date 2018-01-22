import { TestBed, inject } from '@angular/core/testing';

import { FftService } from './fft.service';

describe('FftService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FftService]
    });
  });

  it('should be created', inject([FftService], (service: FftService) => {
    expect(service).toBeTruthy();
  }));
});
