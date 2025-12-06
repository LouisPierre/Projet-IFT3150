import bluetooth
import subprocess
from pythonosc.udp_client import SimpleUDPClient
import os

REAPER_PATH = "/usr/bin/reaper"
LOAD_SCRIPT = "/home/lpb/.config/REAPER/Scripts/loadTrackState.lua"
PLUGIN_LIST_FILE = "/home/lpb/Documents/IFT3150/reaper_fx_list.txt"
PROGRAMS_FOLDER = "/home/lpb/Documents/IFT3150/programs"

OSC_IP = "127.0.0.1"
OSC_PORT = 8000
osc_client = SimpleUDPClient(OSC_IP, OSC_PORT)

os.makedirs(PROGRAMS_FOLDER, exist_ok=True)

def handle_client(client_sock, address):
    print(f"Accepted connection from {address}")
    try:
        while True:
            data = client_sock.recv(1024)
            if not data:
                continue
            message = data.decode().strip()
            print("Received:", message)

            if message == "connect":
                client_sock.send(b"Connected to server!\n")

            elif message == "run_reascript":
                subprocess.Popen([REAPER_PATH, "-nonewinst", LOAD_SCRIPT])
                client_sock.send(b"Reaper script triggered!\n")

            elif message.startswith("volume:"):
                try:
                    v = float(message.split(":")[1])
                    v = max(0.0, min(1.0, v))
                    osc_client.send_message("/track/01/volume", v)
                    client_sock.send(f"Volume set to {v*100:.0f}%\n".encode())
                except Exception as e:
                    client_sock.send(f"Error: {e}\n".encode())

            elif message == "get_plugins":
                try:
                    with open(PLUGIN_LIST_FILE) as f:
                        plugins = f.read().splitlines()
                    for plugin in plugins:
                        client_sock.send((plugin + "\n").encode())
                    client_sock.send(b"END_OF_PLUGINS\n")
                except Exception as e:
                    client_sock.send(f"Error reading plugin list: {e}\n".encode())

            elif message.startswith("create_program:"):
                try:
                    program_name = message.split(":", 1)[1]
                    filepath = os.path.join(PROGRAMS_FOLDER, f"{program_name}.txt")
                    with open(filepath, "w") as f:
                        f.write("")
                    client_sock.send(f"Program {program_name} created!\n".encode())
                except Exception as e:
                    client_sock.send(f"Error creating program: {e}\n".encode())

            else:
                client_sock.send(f"Unknown command: {message}\n".encode())

    except bluetooth.BluetoothError as e:
        print(f"Bluetooth connection lost: {e}")
    finally:
        client_sock.close()
        print(f"Connection from {address} closed.")


def run_server():
    server_sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
    server_sock.bind(("", 1))
    server_sock.listen(1)
    print("Bluetooth RFCOMM server running on channel 1")

    while True:
        print("Waiting for connection...")
        try:
            client_sock, address = server_sock.accept()
            handle_client(client_sock, address)
        except bluetooth.BluetoothError as e:
            print(f"Error accepting connection: {e}")
            continue

if __name__ == "__main__":
    run_server()
