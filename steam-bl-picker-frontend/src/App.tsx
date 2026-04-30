import { useState } from 'react'
import './App.css'

function App() {
    const [apiKey, setAPIKey] = useState("")
    const [userID, setUserID] = useState("")
    const [phase, setPhase] = useState(false)
    const [result, setResult] = useState("")

    async function handleSubmit() {
        try {
                const response = await fetch(
                    `http://localhost:8080/api/pick?apiKey=${apiKey}&steamId=${userID}`
                );

                if (!response.ok) {
                    const errorJson = await response.json();
                    setResult("unsuccessful");
                    return;
                }

                const game = await response.text();
                setResult("You should play '" + game + "' today");

            } catch (error) {
                setResult("unsuccessful");
            }
    }

    return (
        <>
            <div className="wrapper">
                <div>Backlog Picker</div>

                <div>
                    <input
                      className="input"
                      type="password"
                      placeholder="API Key"
                      value={apiKey}
                      onChange={(e) => setAPIKey(e.target.value)}
                    />
                </div>

                <div>
                    <input
                        className="input"
                        type="text"
                        placeholder="Steam ID"
                        value={userID}
                        onChange={(e) => setUserID(e.target.value)}
                    />
              </div>

                <button
                    onClick={handleSubmit}
                >Enter</button>
            </div>
            <div>
                {result}
            </div>
        </>
    )
}

export default App
