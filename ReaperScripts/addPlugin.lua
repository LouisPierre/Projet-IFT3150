--[[
  Adds a plugin to Track 1 in Reaper.
  Reads the plugin name from a file "plugin_name.txt" in the Reaper resource path.
--]]

-- Get Reaper's resource path (where we can store the file)
local resource_path = reaper.GetResourcePath()
local filename = resource_path .. "/plugin_name.txt"

-- Read plugin name from file
local file = io.open(filename, "r")
if not file then
    reaper.ShowMessageBox("Could not open " .. filename, "Error", 0)
    return
end

local plugin_name = file:read("*l") -- read first line
file:close()

if not plugin_name or plugin_name == "" then
    reaper.ShowMessageBox("Plugin name is empty in file", "Error", 0)
    return
end

-- Get Track 1
local track = reaper.GetTrack(0, 0) -- 0-based index
if not track then
    reaper.ShowMessageBox("Track 1 does not exist!", "Error", 0)
    return
end

-- Add FX by name
-- true = add as effect (not JSFX)
local fx_index = reaper.TrackFX_AddByName(track, plugin_name, false, 1)
if fx_index == -1 then
    reaper.ShowMessageBox("Could not find plugin: " .. plugin_name, "Error", 0)
else
    reaper.ShowMessageBox("Plugin added: " .. plugin_name, "Success", 0)
end
