package com.url.service;

import com.url.rest.dto.AddLinkDto;
import com.url.rest.dto.LinkDto;

import java.util.List;

public interface LinkService {

    LinkDto addLink(AddLinkDto addLinkDto);

    List<String> suggestedTags(String link);
}
