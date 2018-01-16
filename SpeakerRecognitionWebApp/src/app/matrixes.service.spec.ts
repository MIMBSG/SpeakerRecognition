import { TestBed, inject } from '@angular/core/testing';

import { MatrixesService } from './matrixes.service';

describe('MatrixesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MatrixesService]
    });
  });

  it('should be created', inject([MatrixesService], (service: MatrixesService) => {
    expect(service).toBeTruthy();
  }));
});
