import React, { ChangeEvent } from 'react';
import './style.css';

type PickerProps = {
	label: string,
	value: string,
	onChange: (event: ChangeEvent<HTMLInputElement>) => void
}

export const Picker = ({ label, value, onChange }: PickerProps) => {

	return (
		<div className='picker'>
			<label className="custom-input__label">{label}</label>
			<input
				className="custom-input"
				value={value}
				onChange={onChange}
				type='datetime-local'
				name={label}
				id={label}
				step='1' />
		</div>
	)
}