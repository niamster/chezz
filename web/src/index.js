import React from 'react';
import ReactDOM from 'react-dom';

import './index.css';

class Index extends React.Component {
  render() {
    return (
      <div className="greeting">
        Wellcome!
      </div>
    );
  }
}

// ========================================

ReactDOM.render(
  <Index />,
  document.getElementById('root')
);
