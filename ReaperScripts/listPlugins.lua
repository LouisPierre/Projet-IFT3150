local path = "/home/lpb/Documents/IFT3150/reaper_fx_list.txt"

local file = io.open(path, "w")
if not file then
    reaper.ShowConsoleMsg("ERROR: Cannot open file: " .. path .. "\n")
    return
end

for i = 0, 100000 do
    -- Some REAPER builds return:  ok = boolean, name = string
    -- Others return: ok = integer, name = string
    local ok, name = reaper.EnumInstalledFX(i, "")

    -- Stop if name is nil or empty
    if not name or name == "" then
        break
    end

    file:write(name .. "\n")
end

file:close()

reaper.ShowConsoleMsg("Plugin list written to:\n" .. path .. "\n")
