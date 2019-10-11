import React, { useState, FormEvent } from 'react';
import { Navbar } from '../../sections';
import './style.css';
import { VideoExcerptService } from '../../services';
import { getUnixTime } from 'date-fns';

export const Home: React.FC = () => {
	const [startDate, setStartDate] = useState<string>("");
	const [endDate, setEndDate] = useState<string>("");
	const videoExcerptService = new VideoExcerptService();

	const handleOnSubmit = (event: FormEvent) => {
		event.preventDefault();
		const instantFromStartDate: number = getUnixTime(new Date(startDate));
		const instantFromEndDate: number = getUnixTime(new Date(endDate));
		const video = videoExcerptService.getVideoExcerpt(instantFromStartDate, instantFromEndDate);
	}

	return <form className='home' onSubmit={handleOnSubmit}>
		<Navbar />
		<div className='time-picker'>
			<div className='picker'>
				<label className="custom-input__label">Start</label>
				<input
					className="custom-input"
					value={startDate}
					onChange={e => setStartDate(e.currentTarget.value)}
					type='datetime-local'
					name=''
					id='' />
			</div>
			<div className='picker'>
				<label className="custom-input__label">End</label>
				<input
					className="custom-input"
					value={endDate}
					onChange={e => setEndDate(e.currentTarget.value)}
					type='datetime-local'
					name=''
					id='' />
			</div>
		</div>
		<button className="submit-button" type="submit">Ok</button>
	</form>
};
