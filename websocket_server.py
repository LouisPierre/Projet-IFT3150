import asyncio
import websockets
import subprocess
from pythonosc.udp_client import SimpleUDPClient

# Paths
REAPER_PATH = "/usr/bin/reaper"
SCRIPTS_DIR = "/home/lpb/.config/REAPER/Scripts/"
LOAD_SCRIPT = SCRIPTS_DIR + "loadTrackState.lua"

PLUGIN_LIST_FILE = "/home/lpb/Documents/IFT3150/reaper_fx_list.txt"

# OSC client for volume control
OSC_IP = "127.0.0.1"
OSC_PORT = 8000
osc_client = SimpleUDPClient(OSC_IP, OSC_PORT)

async def handler(websocket):
    print("Client connected")
    try:
        async for message in websocket:
            print(f"Received: {message}")

            if message == "connect":
                await websocket.send("Connected to server!")
                print("Client connected acknowledged.")

            elif message == "run_reascript":
                print("Running Reaper script...")
                subprocess.Popen([REAPER_PATH, "-nonewinst", LOAD_SCRIPT])
                await websocket.send("Reaper script triggered!")

            elif message.startswith("volume:"):
                try:
                    volume = float(message.split(":")[1])
                    volume = max(0.0, min(1.0, volume))  # clamp 0.0-1.0
                    osc_client.send_message("/track/01/volume", volume)
                    await websocket.send(f"Volume set to {volume*100:.0f}%")
                    print(f"Track 1 volume set to {volume}")
                except Exception as e:
                    await websocket.send(f"Error setting volume: {e}")

            elif message == "get_plugins":
                try:
                    with open(PLUGIN_LIST_FILE, "r") as f:
                        plugins = f.read().splitlines()
                    # Send as a single string joined by a special separator, e.g., newline
                    await websocket.send("\n".join(plugins))
                    print(f"Sent {len(plugins)} plugins to client")
                except Exception as e:
                    await websocket.send(f"Error reading plugin list: {e}")

            else:
                await websocket.send(f"Unknown command: {message}")

    except websockets.ConnectionClosed:
        print("Client disconnected")


async def main():
    async with websockets.serve(handler, "0.0.0.0", 8765):
        print("WebSocket server running on ws://0.0.0.0:8765")
        await asyncio.Future()  # run forever


if __name__ == "__main__":
    asyncio.run(main())
