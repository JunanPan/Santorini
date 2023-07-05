import React from 'react';
import './App.css'; // import the css file to enable your styles.
import { GameState, Grid } from './game';
import BoardGrid from './Grid';

/**
 * Define the type of the props field for a React component
 */
interface Props { }


class App extends React.Component<Props, GameState> {
  private currentPeriod:String = "set";

  /**
   * @param props has type Props
   */
  constructor(props: Props) {
    super(props)
    /**
     * state has type GameState as specified in the class inheritance.
     */
    this.state = {
      grids: [],
      currentPlayer:1,
      selectedCardPlayer1: "None",
      selectedCardPlayer2: "None",
      gameMode: "player",
      Confirm:false
    };
  }


  resetGame = () => {
    this.currentPeriod = "set";
    this.setState({ grids: [],currentPlayer:1});
  }

  handleCardSelected = async (player: number, card: string) => {
    if (player === 1) {
      this.setState({ selectedCardPlayer1: card });
    } else {
      this.setState({ selectedCardPlayer2: card });
    }
  };
  renderCardSelectionForPlayer(player: number) {
    return (
      <div>
        <h3>player{player} Choose Card</h3>
        <select
          onChange={(e) => this.handleCardSelected(player, e.target.value)}
        >
          <option value="Demeter">Demeter</option>
          <option value="Minotaur">Minotaur</option>
          <option value="Pan">Pan</option>
          <option value="Apollo">Apollo</option>
          <option value="Artemis">Artemis</option>
          <option value="Athena">Athena</option>
          <option value="Hephaestus">Hephaestus</option>
          <option value="Atlas">Atlas</option>
          <option value="None"selected>None</option>
          
        </select>
      </div>
    );
  }
  handleGameModeSelected = (mode: string) => {
    this.setState({ gameMode: mode });
  };

  renderGameModeSelection() {
    return (
      <div>
        <h3>Choose Game Mode</h3>
        <select
          onChange={(e) => this.handleGameModeSelected(e.target.value)}
        >
          <option value="player" selected>Player</option>
          <option value="ai">AI</option>
        </select>
      </div>
    );
  }
  
  
    handleConfirmSelection = () => {

        if((this.state.selectedCardPlayer1==="Pan"&&this.state.selectedCardPlayer2!=="None"&&this.state.selectedCardPlayer2!=="Pan")||
        (this.state.selectedCardPlayer2==="Pan"&&this.state.selectedCardPlayer1!=="None"&&this.state.selectedCardPlayer1!=="Pan")){
          alert("Pan can not be used along with other cards. Only Pan+Pan or Pan+None is allowed.");
        }
        else if((this.state.selectedCardPlayer1==="Athena"&&this.state.selectedCardPlayer2!=="None"&&this.state.selectedCardPlayer2!=="Athena")||
        (this.state.selectedCardPlayer2==="Athena"&&this.state.selectedCardPlayer1!=="None"&&this.state.selectedCardPlayer1!=="Athena")){
          alert("Athena can not be used along with other cards. Only Athena+Athena or Athena+None is allowed.");
        }
        else{
          this.setCard();
          this.setState({Confirm:true})
        }

    };

  setCard =async () => {
    const response1 = await fetch(
      `/card?player1Card=${this.state.selectedCardPlayer1}&player2Card=${this.state.selectedCardPlayer2}`
    );
    console.log(response1);
  }
  newGame = async () => {
    this.resetGame();
    const response = await fetch('/newgame');
    const json = await response.json();
    this.setState({ grids: json['grids'] });
    this.setState({currentPlayer:json['currentPlayer']})
    this.currentPeriod = json['currentPeriod'];
  }

  restartGame = async () => {
    window.location.reload();
  };
  

  undo = async () => {
    const response = await fetch('/undo');
    const json = await response.json();
    this.setState({ grids: json['grids'] });
    this.setState({currentPlayer:json['currentPlayer']})
    this.currentPeriod = json['currentPeriod'];
  }
  /**
   * play will generate an anonymous function that the component
   * can bind with.
   * @param x 
   * @param y 
   * @returns 
   */
  play(x: number, y: number): React.MouseEventHandler {
    return async (e) => {
      // prevent the default behavior on clicking a link; otherwise, it will jump to a new page.
      e.preventDefault();
      const response = await fetch(`/play?x=${x}&y=${y}`)
      const text = await response.text();
      
      if(text==="invalid"){
        alert('invalid operation');
      }
      else{

      const json = JSON.parse(text);
      const gameOver = json['gameOver'];
      const winner = json['winner'];
      if(gameOver===true){
        setTimeout(() => {
          alert(`gameOver! Player ${winner} win!`);
          this.newGame();
        }, 0);
        
      }
      this.setState({ grids: json['grids']});
      this.setState({currentPlayer:json['currentPlayer']})
      this.currentPeriod = json['currentPeriod'];
      
      }

    }
  }

  renderCardSelection() {
    return (
      <div>
        <h2>Choose Cards</h2>
        {this.renderCardSelectionForPlayer(1)}
        {this.renderCardSelectionForPlayer(2)}
        
      </div>
    );
  }

  createPlayGrid(grid: Grid, index: number): React.ReactNode {
    return (
    <div key={index}>
      <a href='/' onClick={this.play(grid.x,grid.y)}>
        <BoardGrid grid={grid}></BoardGrid>
      </a>
    </div>
  )
  }

  renderCardDescription() {
    const cardDescriptions = {
      Demeter: "Demeter: You can build twice(not the same place). Click your selected worker to pass the second build.",
      Minotaur: "Minotaur: Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.",
      Pan: "Pan: Player wins if their worker moves down two or more levels. (Due to my design flaw, you have to build one more time to know you win.)",
      Apollo:"Your Worker may move into an opponent Worker's space by forcing their Worker to the space you just vacated.",
      Artemis:"Your Worker may move one additional time, but not back to its initial space. Click your selected worker to pass the second move.",
      Athena:"During opponent's turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn",
      Hephaestus:"Your Worker may build one additional block (not dome) on top of your first block. Click your selected worker to pass the second build.",
      Atlas:": Your Worker may build a dome at any level for the second build. It should be on your first build position. Click your selected worker to pass the second build.",
      None: "None: No special ability.",
    };
    const currentPlayerCard =
      this.state.currentPlayer === 1
        ? this.state.selectedCardPlayer1
        : this.state.selectedCardPlayer2
        return (
          <div>
            Current Card: {currentPlayerCard}<br/>
            {cardDescriptions[currentPlayerCard]}<br/>
          </div>
        );
        
  }
  
  aiPlay = async () => {
    let isValid = false;
    while (!isValid) {
      const x = Math.floor(Math.random() * 5);
      const y = Math.floor(Math.random() * 5);
      const response = await fetch(`/play?x=${x}&y=${y}`);
      const text = await response.text();
  
      if (text !== "invalid") {
        isValid = true;
        const json = JSON.parse(text);
        const gameOver = json['gameOver'];
        const winner = json['winner'];
        if (gameOver === true) {
          setTimeout(() => {
            alert(`gameOver! Player ${winner} win!`);
            this.newGame();
          }, 0);
        }
        this.setState({ grids: json['grids'] });
        this.setState({currentPlayer:json['currentPlayer']});;
        this.currentPeriod = json['currentPeriod'];
      }
    }
  };
  
  componentDidUpdate(prevProps: Props, prevState: GameState) {
    if (this.state.gameMode === 'ai' && this.state.currentPlayer === 2) {
      this.aiPlay();
    }
  }
  render(): React.ReactNode {
    if (!this.state.Confirm) {
      return (
        <div>
          {this.renderCardSelection()}
          {this.renderGameModeSelection()}
          <button onClick={this.handleConfirmSelection}>Confirm</button> <br/>
          <b>God Cards Introduction: </b><br/>
          <b>Demeter</b>: You can build twice(not the same place). Click your selected worker to pass the second build. <br/>
          <b>Minotaur</b>: Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any
level. <br/>
          <b>Pan</b>: Player wins if their worker moves down two or more levels. (Due to my design flaw, you have to build one more time to know you win.) <br/>
          <b>Apollo</b>: Your Worker may move into an opponent Worker's space by forcing their Worker to the space you just vacated. <br/>
          <b>Artemis</b>: Your Worker may move one additional time, but not back to its initial space. Click your selected worker to pass the second move. <br/>
          <b>Athena</b>: During opponent's turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn <br/>
          <b>Hephaestus</b>: Your Worker may build one additional block (not dome) on top of your first block. Click your selected worker to pass the second build. <br/>
          <b>Atlas</b>: Your Worker may build a dome at any level for the second build. It should be on your first build position. Click your selected worker to pass the second build, <br/>
          <b>None</b>: None: No special ability. <br/>
          <b>Note: Due to my design flaw, Pan and Athena can not be used with other different cards. It means if a player choose Pan, another player can only choose Pan or None.</b>
        </div>
      );
    }
    // this.componentDidUpdate(this.state,this.state) {
    //   if (this.state.gameMode === 'ai' && this.state.currentPlayer === 2) {
    //     this.aiPlay();
    //   }
    // }
      return (
      <div>
        <div id="bottombar">
          <button onClick={this.newGame}>New Game</button>
          <button onClick={this.undo}>Undo</button>
        </div>
        <div id="board">



          {this.state.grids.map((grid, i) => this.createPlayGrid(grid, i))}
        </div>
        <div id="display">
          ⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️ <br/>
          Please Click "New Game" to start. <br/>
          Current Period:{this.currentPeriod} <br/>
          Current player:{this.state.currentPlayer} <br/>
          {this.renderCardDescription()}
          
        <button onClick={this.restartGame}>Reselect Gard cards</button>
        </div>
      </div>
    );
}
}
export default App;
