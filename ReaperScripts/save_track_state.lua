-- Output Track 1's state chunk to the REAPER resource folder
function OutputTrackStateToFile()
    -- Get Track 1 (index 0)
    local track = reaper.GetTrack(0, 0)
    if not track then
        reaper.ShowMessageBox("Track 1 does not exist!", "Error", 0)
        return
    end

    -- Get the track state chunk
    local _, trackStateChunk = reaper.GetTrackStateChunk(track, "", false)
    if not trackStateChunk or trackStateChunk == "" then
        reaper.ShowMessageBox("Could not retrieve track state chunk!", "Error", 0)
        return
    end

    -- Get the REAPER resource path
    local resourcePath = reaper.GetResourcePath()
    local filePath = resourcePath .. "/Track1_StateChunk.txt"

    -- Write to file
    local file = io.open(filePath, "w")
    if not file then
        reaper.ShowMessageBox("Could not open file for writing!", "Error", 0)
        return
    end

    file:write(trackStateChunk)
    file:close()

    reaper.ShowMessageBox("Track state chunk saved to:\n" .. filePath, "Success", 0)
end

-- Run the function
OutputTrackStateToFile()
