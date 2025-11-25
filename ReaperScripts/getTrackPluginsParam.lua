
-- Get Track 1 (index 0)
local track = reaper.GetTrack(0, 0)
if not track then
    reaper.ShowMessageBox("Track 1 not found!", "Error", 0)
    return
end

-- Get Reaper resources folder
local resource_path = reaper.GetResourcePath()
local file_path = resource_path .. "/Track1_FX_Settings.txt"

-- Open file for writing
local file = io.open(file_path, "w")
if not file then
    reaper.ShowMessageBox("Cannot open file for writing!", "Error", 0)
    return
end

local fx_count = reaper.TrackFX_GetCount(track)

for fx = 0, fx_count-1 do
    -- Get FX name
    local retval, fx_name = reaper.TrackFX_GetFXName(track, fx, "")
    file:write("FX: " .. fx_name .. "\n")

    -- Get number of parameters
    local param_count = reaper.TrackFX_GetNumParams(track, fx)

    for p = 0, param_count-1 do
        -- Get parameter name and value
        local retval, param_name = reaper.TrackFX_GetParamName(track, fx, p, "")
        local value = reaper.TrackFX_GetParam(track, fx, p)  -- normalized 0-1 value
        file:write("  " .. param_name .. ": " .. value .. "\n")
    end
    file:write("\n")
end

file:close()
reaper.ShowMessageBox("FX parameters exported successfully!\nSaved to:\n" .. file_path, "Done", 0)
