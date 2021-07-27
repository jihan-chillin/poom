package com.chairking.poom.hashTag.model.service;

import java.util.List;
import java.util.Map;

public interface TagService {
    int insertTag(String tag);
    List getMyTagData(String loginId);
}
