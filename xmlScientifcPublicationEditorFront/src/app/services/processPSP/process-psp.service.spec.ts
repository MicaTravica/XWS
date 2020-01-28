import { TestBed } from '@angular/core/testing';

import { ProcessPSPService } from './process-psp.service';

describe('ProcessPSPService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProcessPSPService = TestBed.get(ProcessPSPService);
    expect(service).toBeTruthy();
  });
});
