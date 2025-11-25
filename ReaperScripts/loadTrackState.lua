-- Load entire track state of Track 1 from a file
function LoadTrackStateFromFile()
    -- Get Track 1 (index 0)
    local track = reaper.GetTrack(0, 0)
    if not track then
        reaper.ShowMessageBox("Track 1 does not exist!", "Error", 0)
        return
    end

    -- Get the REAPER resource path
    local resourcePath = reaper.GetResourcePath()
    local filePath = resourcePath .. "/Track1_StateChunk.txt"

    -- Read the track state from the file
    local file = io.open(filePath, "r")
    if not file then
        reaper.ShowMessageBox("Could not open track state file!", "Error", 0)
        return
    end

    local trackStateChunk = file:read("*a")
    file:close()

    -- Set the track state chunk
    local ret = reaper.SetTrackStateChunk(track, trackStateChunk, false)
    if not ret then
        reaper.ShowMessageBox("Could not set track state chunk!", "Error", 0)
        return
    end

    reaper.UpdateArrange()
    reaper.ShowMessageBox("Track state loaded into Track 1 successfully!", "Success", 0)
end

-- Run the function
LoadTrackStateFromFile()
