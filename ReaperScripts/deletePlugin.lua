-- Path to the file containing the plugin name
local file_path = "/home/lpb/.config/REAPER/plugin_dlt_name.txt"

-- Read the plugin name
local file = io.open(file_path, "r")
if not file then
    reaper.ShowMessageBox("Cannot open file: " .. file_path, "Error", 0)
    return
end

local plugin_name = file:read("*line")
file:close()

if not plugin_name or plugin_name == "" then
    reaper.ShowMessageBox("Plugin name is empty in the file.", "Error", 0)
    return
end

-- Get track 1
local track = reaper.GetTrack(0, 0)
if not track then
    reaper.ShowMessageBox("Track 1 not found.", "Error", 0)
    return
end

-- Function to normalize FX name for comparison
local function normalize(name)
    -- remove text in parentheses and trim spaces
    name = name:gsub("%(.-%)", "")  -- remove anything inside ()
    name = name:gsub("^%s*(.-)%s*$", "%1") -- trim spaces
    return name:lower()
end

local plugin_name_norm = normalize(plugin_name)

-- Iterate backwards through FX
local fx_count = reaper.TrackFX_GetCount(track)
local deleted = false

for i = fx_count - 1, 0, -1 do
    local retval, fx_name = reaper.TrackFX_GetFXName(track, i, "")
    local fx_name_norm = normalize(fx_name)
    if fx_name_norm:find(plugin_name_norm, 1, true) then  -- true = plain text search
        reaper.TrackFX_Delete(track, i)
        deleted = true
    end
end

if deleted then
    reaper.ShowMessageBox("Plugin(s) deleted successfully.", "Success", 0)
else
    reaper.ShowMessageBox("No plugin matching '" .. plugin_name .. "' found on track 1.", "Info", 0)
end
