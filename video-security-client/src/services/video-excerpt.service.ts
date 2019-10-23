import { BaseService } from './base.service';

export class VideoExcerptService extends BaseService {

	CAMERA_ID: number = 1; 

	constructor() {
		super('/fabric');
	}

	async getVideoExcerpt(startInstant: number, endInstant: number): Promise<any> {
		return super.get(`/find-by-camera-id-and-timestamp-range/${this.CAMERA_ID}?startDate=${startInstant}&endDate=${endInstant}`);
	}

}