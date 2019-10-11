import React from 'react';
import { Route } from 'react-router-dom';
import { Home } from './screens';

export const App: React.FC = () => (
	<>
		<Route path='/' component={Home} exact />
	</>
);
