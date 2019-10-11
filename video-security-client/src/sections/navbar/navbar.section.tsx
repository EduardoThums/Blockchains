import React from 'react';
import './style.css';
import { ReactComponent as Logo } from '../../assets/logo.svg';

export const Navbar: React.FC = () => (
	<div className='navbar'>
		<div className='logo__area'>
			<Logo/>
			<h1 className="app-title">Video Security</h1>
		</div>
	</div>
);
