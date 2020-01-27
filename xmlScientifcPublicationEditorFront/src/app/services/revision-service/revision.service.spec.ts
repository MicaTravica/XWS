import { TestBed } from '@angular/core/testing';

import { RevisionService } from './revision.service';

describe('RevisionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RevisionService = TestBed.get(RevisionService);
    expect(service).toBeTruthy();
  });
});
