import axios, { AxiosInstance } from 'axios'

const BASE_URL: string = 'localhost:8080';

const httpClient = (baseURL: string) => axios.create({
	timeout: 30000,
	headers: {
		'Content-type': 'application/json'
	},
	baseURL
})

export class BaseService {
	client: AxiosInstance;

	constructor(path: string) {
		this.client = httpClient(`${BASE_URL}${path}`);
	}

	async get(url: string) {
		const result = await this.client.get(url)

		return result.data
	}
}
