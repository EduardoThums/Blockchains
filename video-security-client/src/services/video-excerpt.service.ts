import { BaseService } from './base.service';

export class VideoExcerptService extends BaseService {
	constructor() {
		super('/fabric');
	}

	async getVideoExcerpt(startInstant: number, endInstant: number) {
		console.log(`/find-by-camera-id-and-timestamp-range/${1}?startDate=${startInstant}&endDate=${endInstant}`);
	}

}