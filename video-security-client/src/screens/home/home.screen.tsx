import React, { useState, FormEvent } from 'react';
import { Navbar } from '../../sections';
import './style.css';
import { VideoExcerptService } from '../../services';
import { getUnixTime } from 'date-fns';
import { Picker } from '../../components';

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

	return (
		<form className='home' onSubmit={handleOnSubmit}>
			<Navbar />
			<div className='time-picker'>
				<Picker
					label='Início'
					value={startDate}
					onChange={e => setStartDate(e.currentTarget.value)}
				/>
				<Picker
					label='Fim'
					value={endDate}
					onChange={e => setEndDate(e.currentTarget.value)}
				/>
			</div>
			<button className="submit-button" type="submit">Ok</button>
		</form>
	)
};
