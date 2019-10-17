package com.url.service;

import com.url.rest.dto.AddLinkDto;
import com.url.rest.dto.LinkDto;

public interface LinkService {

    LinkDto addLink(AddLinkDto addLinkDto);
}
