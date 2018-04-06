function from_private(meta)
    -- meta = 'AABBCCDD' 字节数据以16进制文本表示
    -- 经过处理后转换成JSON字符串 --
    return '{}' -- JSON字符串 --
end

function to_private(jsonstr)
    -- jsonstr JSON字符串
    -- 经过处理后转换成16进制表示的字节数据 --
    return 'AABBCCDD'
end

