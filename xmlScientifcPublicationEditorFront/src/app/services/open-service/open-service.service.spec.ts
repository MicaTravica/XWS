import { TestBed } from '@angular/core/testing';

import { OpenServiceService } from './open-service.service';

describe('OpenServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OpenServiceService = TestBed.get(OpenServiceService);
    expect(service).toBeTruthy();
  });
});
