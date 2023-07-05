import React from 'react';
import { Grid } from './game';

interface Props {
  grid: Grid
}

function formatGrid(grid:Grid) {
  if(grid.player===0){
    if (grid.blockHeight === 0) {
      return '';
    } else if (grid.blockHeight === 1) {
      return '[]';
    } else if (grid.blockHeight === 2) {
      return '[[]]';
    } else if (grid.blockHeight === 3) {
      return '[[[]]]';
    }else if (grid.blockHeight === 4) {
      return '[[[o]]]';
    }
  }
  else{
    if (grid.blockHeight === 0) {
      return grid.player;
    } else if (grid.blockHeight === 1) {
      return `[${grid.player}]`;
    } else if (grid.blockHeight === 2) {
      return `[[${grid.player}]]`;
    } else if (grid.blockHeight === 3) {
      return `[[[${grid.player}]]]`;
    }
  } 
}


class BoardGrid extends React.Component<Props> {
  
  render(): React.ReactNode {
    const playable = this.props.grid.playable ? 'playable' : '';
    return (
      <div className={`grid ${playable}`}>
        <p >{this.props.grid.x},{this.props.grid.y}</p>
        {formatGrid(this.props.grid)}</div>
    )
  }
}

export default BoardGrid;